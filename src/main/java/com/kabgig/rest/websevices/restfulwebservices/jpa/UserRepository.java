package com.kabgig.rest.websevices.restfulwebservices.jpa;

import com.kabgig.rest.websevices.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
