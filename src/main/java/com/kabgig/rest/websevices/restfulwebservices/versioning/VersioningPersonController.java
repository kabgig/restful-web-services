package com.kabgig.rest.websevices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    //URI versioning
    @GetMapping("/v1/person")
    public PersonV1 GetFirstVersionOfPerson(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 GetSecondVersionOfPerson(){
        return new PersonV2(new Name("Bob","Charlie"));
    }

    //Request Parameter versioning
    @GetMapping(path="/person", params = "version=1")
    public PersonV1 GetFirstVersionOfPersonRequestParameter(){
        return new PersonV1("Bob Charlie V1");
    }

    @GetMapping(path="/person", params = "version=2")
    public PersonV2 GetSecondVersionOfPersonRequestParameter(){
        return new PersonV2(new Name("Bob","Charlie V2"));
    }

    //Custom header versioning
    @GetMapping(path="/person/header", headers = "X-API-VERSION=1")
    public PersonV1 GetFirstVersionOfPersonRequestHeader(){
        return new PersonV1("Bob Charlie V1 - request header");
    }

    @GetMapping(path="/person/header", headers = "X-API-VERSION=2")
    public PersonV2 GetSecondVersionOfPersonRequestHeader(){
        return new PersonV2(new Name("Bob","Charlie V2"));
    }

    //Media type versioning
    @GetMapping(path="/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 GetFirstVersionOfPersonAcceptHeader(){
        return new PersonV1("Bob Charlie V1 - request header");
    }

    @GetMapping(path="/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 GetSecondVersionOfPersonAcceptHeader(){
        return new PersonV2(new Name("Bob","Charlie V2"));
    }


}
