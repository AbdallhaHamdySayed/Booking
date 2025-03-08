package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.database.entity.*;
import com.AlTaraf.Booking.database.repository.*;
import com.AlTaraf.Booking.rest.dto.CityDtoSample;
import com.AlTaraf.Booking.rest.dto.CounterUser;
import com.AlTaraf.Booking.rest.dto.PasswordResetDto;
import com.AlTaraf.Booking.rest.dto.UserRegisterDto;
import com.AlTaraf.Booking.rest.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

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

    public Boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            // Handle empty email case, e.g., return false or throw an exception
            return false;
        }

        return userRepository.existsByEmail(email);
    }

    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User registerUser(UserRegisterDto userRegisterDto) {
        String phone = userRegisterDto.getPhoneNumber();
        Optional<User> existingUserOptional = userRepository.findByPhone(phone);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            Set<String> strRoles = userRegisterDto.getRoles();
            Set<Role> newRoles = new HashSet<>();

            if (strRoles != null) {
                strRoles.forEach(roleName -> {
                    Role role = roleRepository.findByName(ERole.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(role);
                });
            }

            boolean rolesChanged = false;
            for (Role newRole : newRoles) {
                if (!existingUser.getRoles().contains(newRole)) {
                    existingUser.getRoles().add(newRole);
                    rolesChanged = true;
                }
            }

            if (rolesChanged) {
                return userRepository.save(existingUser);
            } else {
                return existingUser;
            }
        } else {
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

            CityDtoSample DtoSample = userRegisterDto.getCity();
            City city = cityMapper.cityDTOSampleToCity(DtoSample);
            User user = new User();
            user.setUsername(userRegisterDto.getName());
            user.setEmail(userRegisterDto.getEmail());
            user.setPassword(encoder.encode(userRegisterDto.getPassword()));
            user.setPhone(phone);
            user.setCity(city);
            user.setRoles(roles);

            return userRepository.save(user);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void resetPasswordByPhone(String phone, PasswordResetDto passwordResetDto) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(passwordResetDto.getNewPassword()));

        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

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
        user.setUuidAds(UUID.randomUUID().toString());

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
    public CounterUser getCountUser() {
        CounterUser counterUser = new CounterUser();

        counterUser.setCounterAllUsers(userRepository.countAllUsers());
        counterUser.setCounterUserGuest(userRepository.countUsersByRoleIdOne());
        counterUser.setCounterUserLessor(userRepository.countUsersByRoleIdTwo());

        return counterUser;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<User> getAllByRolesName(ERole roleName) {
        return userRepository.findAllByRoles_Name(roleName);
    }

    public void deleteUsersWithIsActiveNull() {

        List<User> users = userRepository.findAllUserIsNotActive();

        for (User user : users) {
            deleteUserAndAssociatedEntities(user.getId());
        }
    }

    public User updateWallet(Long userId, Double newWalletAmount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setWallet(newWalletAmount);
        return userRepository.save(user);
    }


    public User updateActive(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setIsActive(true);
        return userRepository.save(user);
    }

    public void setActive(Long unitId) {
        userRepository.activateUserById(unitId);
    }

    public void addingWallet(User user, double commision) {
        double currentWallentBalance = user.getWallet();
        System.out.println("currentWallentBalance: " + currentWallentBalance);

        currentWallentBalance -= commision;
        System.out.println("currentWallentBalance: " + currentWallentBalance);

        user.setWallet(currentWallentBalance);

        userRepository.save(user);
    }

    public void subtractWallet(User user, double commision) {
        double currentWallentBalance = user.getWallet();
        System.out.println("currentWallentBalance: " + currentWallentBalance);

        currentWallentBalance += commision;
        System.out.println("currentWallentBalance: " + currentWallentBalance);

        user.setWallet(currentWallentBalance);

        userRepository.save(user);
    }
}

