package com.kabgig.rest.websevices.restfulwebservices.jpa;

import com.kabgig.rest.websevices.restfulwebservices.user.Post;
import com.kabgig.rest.websevices.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {



}
