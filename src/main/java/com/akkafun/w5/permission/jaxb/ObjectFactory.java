//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.16 at 12:03:33 PM CST 
//


package com.akkafun.w5.permission.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.lianhua.permission.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.lianhua.permission.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Permissions }
     * 
     */
    public Permissions createPermissions() {
        return new Permissions();
    }

    /**
     * Create an instance of {@link Permissions.Role }
     * 
     */
    public Permissions.Role createPermissionsRole() {
        return new Permissions.Role();
    }

    /**
     * Create an instance of {@link Permissions.Resource }
     * 
     */
    public Permissions.Resource createPermissionsResource() {
        return new Permissions.Resource();
    }

    /**
     * Create an instance of {@link Permissions.User }
     * 
     */
    public Permissions.User createPermissionsUser() {
        return new Permissions.User();
    }

    /**
     * Create an instance of {@link Permissions.Role.Operation }
     * 
     */
    public Permissions.Role.Operation createPermissionsRoleOperation() {
        return new Permissions.Role.Operation();
    }

    /**
     * Create an instance of {@link Permissions.Resource.Operation }
     * 
     */
    public Permissions.Resource.Operation createPermissionsResourceOperation() {
        return new Permissions.Resource.Operation();
    }

}
