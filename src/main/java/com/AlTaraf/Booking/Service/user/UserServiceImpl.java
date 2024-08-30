package com.AlTaraf.Booking.Service.user;

import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.Transactions.TotalTransactions;
import com.AlTaraf.Booking.Entity.Transactions.Transactions;
import com.AlTaraf.Booking.Entity.Transactions.TransactionsDetail;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.Mapper.city.CityMapper;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;
import com.AlTaraf.Booking.Payload.response.CounterUser;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForAdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForPdfRepository;
import com.AlTaraf.Booking.Repository.File.FileForProfileRepository;
import com.AlTaraf.Booking.Repository.File.FileForUnitRepository;
import com.AlTaraf.Booking.Repository.NotificationRepository;
import com.AlTaraf.Booking.Repository.Transactions.TotalTransactionsRepository;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsDetailRepository;
import com.AlTaraf.Booking.Repository.Transactions.TransactionsRepository;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.Repository.Wallet.WalletRepository;
import com.AlTaraf.Booking.Repository.payment.PayemntRepository;
import com.AlTaraf.Booking.Repository.role.RoleRepository;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.unit.UnitService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TechnicalSupportRepository technicalSupportRepository;

    @Autowired
    UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    FileForAdsRepository fileForAdsRepository;

    @Autowired
    FileForUnitRepository fileForUnitRepository;

    @Autowired
    FileForProfileRepository fileForProfileRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    UnitService unitService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PackageAdsRepository packageAdsRepository;

    @Autowired
    TotalTransactionsRepository totalTransactionsRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionsDetailRepository transactionsDetailRepository;

    @Autowired
    FileForPdfRepository fileForPdfRepository;

    @Autowired
    PayemntRepository payemntRepository;

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            // Handle empty email case, e.g., return false or throw an exception
            return false;
        }

        return userRepository.existsByEmail(email);
    }
    @Override
    public boolean isDuplicatePhoneNumber(String phone) {
        return userRepository.isDuplicatePhoneNumber(phone);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String phone, Set<ERole> roleNames) {
        return userRepository.existsByEmailAndPhoneNumberAndRoles( phone, roleNames);
    }

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {
        String phone = userRegisterDto.getPhoneNumber();
        Optional<User> existingUserOptional = userRepository.findByPhone(phone);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // User with the same phone number exists, update roles if necessary
            Set<String> strRoles = userRegisterDto.getRoles();
            Set<Role> newRoles = new HashSet<>();

            // Convert role names to Role entities
            if (strRoles != null) {
                strRoles.forEach(roleName -> {
                    Role role = roleRepository.findByName(ERole.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(role);
                });
            }

            // Check if there are new roles to add
            boolean rolesChanged = false;
            for (Role newRole : newRoles) {
                if (!existingUser.getRoles().contains(newRole)) {
                    existingUser.getRoles().add(newRole);
                    rolesChanged = true;
                }
            }

            // If roles are updated, save the user entity
            if (rolesChanged) {
                return userRepository.save(existingUser);
            } else {
                // If roles are not changed, return the existing user
                return existingUser;
            }
        } else {
            // User does not exist, proceed with registration
            // Your existing registration code goes here
            Set<String> strRoles = userRegisterDto.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_GUEST)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (ERole.valueOf(role.toUpperCase())) {
                        case ROLE_LESSOR:
                            Role adminRole = roleRepository.findByName(ERole.ROLE_LESSOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        case ROLE_GUEST:
                            Role modRole = roleRepository.findByName(ERole.ROLE_GUEST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;
                        case ROLE_ADMIN:
                            Role dashRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(dashRole);
                            break;
                        case ROLE_SERVICE:
                            Role serviceRole = roleRepository.findByName(ERole.ROLE_SERVICE)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(serviceRole);
                            break;
                        default:
                            throw new RuntimeException("Error: Unrecognized role provided: " + role);
                    }
                });
            }

            // Check if the city exists
            CityDtoSample DtoSample = userRegisterDto.getCity();
            City city = cityMapper.cityDTOSampleToCity(DtoSample);
//            if (city == null) {
//                throw new RuntimeException("City " + DtoSample.getCityName() + " not found");
//            }

            // Map UserRegisterDto to User entity
            User user = new User();
            user.setUsername(userRegisterDto.getName());
            user.setEmail(userRegisterDto.getEmail());
            user.setPassword(encoder.encode(userRegisterDto.getPassword()));
            user.setPhone(phone); // Use the provided phone number
            user.setCity(city);
            user.setRoles(roles);

            // Save the user entity
            return userRepository.save(user);
        }
    }



    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void resetPasswordByPhone(String phone, PasswordResetDto passwordResetDto) {
        // Retrieve the user from the database using phone number
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the password
        user.setPassword(encoder.encode(passwordResetDto.getNewPassword()));

        // Save the updated user
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // You might want to perform additional validation or business logic here
        userRepository.save(user);
    }


    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Transactional
    public void deleteUserAndAssociatedEntities(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Delete associated entities
            technicalSupportRepository.deleteByUser(user);
            fileForUnitRepository.deleteByUser(user);
            fileForAdsRepository.deleteByUser(user);
            fileForProfileRepository.disassociateByUserId(userId);
            fileForProfileRepository.deleteByUserId(userId);

            fileForPdfRepository.deleteByUserId(userId);
            payemntRepository.deleteByUserId(userId);
            transactionsDetailRepository.deleteByUserId(userId);
            userFavoriteUnitRepository.deleteByUser(user);
            notificationRepository.deleteByUserId(userId);
            walletRepository.deleteByUserId(userId);

            // Delete the units after deleting associated entities
            List<Unit> unitList = unitRepository.findByUser(user);
            for (Unit unit : unitList) {
                unitService.deleteUnitWithDependencies(unit.getId());
            }

            // Delete the units after deleting associated entities
            unitRepository.deleteByUser(user);

            // Finally, delete the user
            userRepository.delete(user);
        }
    }

    public User setPackageAdsForUser(Long userId, Long packageAdsId) throws InsufficientFundsException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        PackageAds packageAds = packageAdsRepository.findById(packageAdsId)
                .orElseThrow(() -> new IllegalArgumentException("PackageAds not found with id: " + packageAdsId));

        if (user.getWallet() < packageAds.getPrice()) {
            throw new InsufficientFundsException("fail_package_ads_wallet.message");
        }

        user.setPackageAds(packageAds);
        user.setNumberAds(packageAds.getNumberAds());

        if (user.getWallet() > 0) {

            double currentWalletBalance = user.getWallet();
            currentWalletBalance -= packageAds.getPrice();
            user.setWallet(currentWalletBalance);

            TotalTransactions totalTransactions = totalTransactionsRepository.findById(1L).orElse(null);

            Long totalSubscriptions = totalTransactions.getTotalSubscriptionsTransactions();
            Long totalTransactionsNumber = totalTransactions.getTotalTransactions();
            totalSubscriptions++;
            totalTransactionsNumber++;

            totalTransactions.setTotalSubscriptionsTransactions(totalSubscriptions);
            totalTransactions.setTotalTransactions(totalTransactionsNumber);
            totalTransactionsRepository.save(totalTransactions);

            Transactions transactions = transactionsRepository.findById(2L).orElse(null);

            TransactionsDetail transactionsDetail = new TransactionsDetail();
            transactionsDetail.setTransactions(transactions);
            transactionsDetail.setDate(new Date());
            transactionsDetail.setPhone(user.getPhone());
            transactionsDetail.setValue(packageAds.getPrice());
            transactionsDetail.setUser(user);

            transactionsDetailRepository.save(transactionsDetail);
        }
        userRepository.save(user);

        return user;
    }

    @Override
    public CounterUser getCountUser() {
        CounterUser counterUser = new CounterUser();

        counterUser.setCounterAllUsers(userRepository.countAllUsers());
        counterUser.setCounterUserGuest(userRepository.countUsersByRoleIdOne());
        counterUser.setCounterUserLessor(userRepository.countUsersByRoleIdTwo());

        return counterUser;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllByRolesName(ERole roleName) {
        return userRepository.findAllByRoles_Name(roleName);
    }

    @Override
    @Transactional
    public void deleteUsersWithIsActiveNull() {

        List<User> users = userRepository.findAllUserIsNotActive();

        for (User user : users) {
            deleteUserAndAssociatedEntities(user.getId());
        }
//        userRepository.deleteUsersWithIsActiveNull();
    }
}
