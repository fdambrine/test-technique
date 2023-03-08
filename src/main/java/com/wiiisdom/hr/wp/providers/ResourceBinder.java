package com.wiiisdom.hr.wp.providers;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.wiiisdom.hr.wp.providers.factories.EntityManagerInjectableFactory;

import jakarta.persistence.EntityManager;

public class ResourceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(new EntityManagerInjectableFactory()).to(EntityManager.class);
    }
}
