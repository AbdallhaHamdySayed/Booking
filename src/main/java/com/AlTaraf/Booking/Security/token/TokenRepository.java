package com.AlTaraf.Booking.Security.token;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(@Param("id") Long id);

  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :userId \s""")
  Page<Token> findAllByUser(@Param("userId") Integer userId , Pageable pageable);

  @Modifying
  @Query("UPDATE Token t SET t.expired = true, t.revoked = true , t.operation = :operation   WHERE t.user.id = :userId ")
  void revokeTokenByUser(@Param("userId") Integer userId , @Param("operation") String operation );

  Optional<Token> findByToken(String token);
}
