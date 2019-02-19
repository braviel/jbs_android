package com.example.jbs.ObjectMapping;

import org.joda.time.LocalTime;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.converter.PropertyConverter;

@Entity
public class Profile {
    @Id public long Id;
    @Index public String ProfileUID;
    public String ProfilePhone;
    public String ProfileEmail;
    public String ProfileUEN;
    public byte[] Photo;
    public String CommonName;
    public String FirstName;
    public String LastName;
    public Date DoB;
    public String Gender;
    public String BuildingName;
    public String Address1;
    public String Address2;
    public String PostalCode;
    public String City;
    public String Country;
    public Date SysDateCreate;
    @Convert(converter = TimeConverter.class, dbType = Long.class)
    public LocalTime SysTimeCreate;
    public Date SysDateUpdate;
    @Convert(converter = TimeConverter.class, dbType = Long.class)
    public LocalTime SysTimeUpdate;

    public Profile() {
    }

    public Profile(long id, String profileUID, String profilePhone, String profileEmail, String profileUEN, byte[] photo, String commonName, String firstName, String lastName, Date doB, String gender, String buildingName, String address1, String address2, String postalCode, String city, String country, Date sysDateCreate, LocalTime sysTimeCreate, Date sysDateUpdate, LocalTime sysTimeUpdate) {
        Id = id;
        ProfileUID = profileUID;
        ProfilePhone = profilePhone;
        ProfileEmail = profileEmail;
        ProfileUEN = profileUEN;
        Photo = photo;
        CommonName = commonName;
        FirstName = firstName;
        LastName = lastName;
        DoB = doB;
        Gender = gender;
        BuildingName = buildingName;
        Address1 = address1;
        Address2 = address2;
        PostalCode = postalCode;
        City = city;
        Country = country;
        SysDateCreate = sysDateCreate;
        SysTimeCreate = sysTimeCreate;
        SysDateUpdate = sysDateUpdate;
        SysTimeUpdate = sysTimeUpdate;
    }

    public static class TimeConverter implements PropertyConverter<LocalTime, Long> {
        @Override
        public LocalTime convertToEntityProperty(Long databaseValue) {
            return new LocalTime(databaseValue);
        }

        @Override
        public Long convertToDatabaseValue(LocalTime entityProperty) {
            return (long)(entityProperty.getMillisOfDay());
        }
    }
}
