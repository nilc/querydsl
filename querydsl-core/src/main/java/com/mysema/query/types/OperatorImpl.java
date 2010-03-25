/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.types;

import static java.util.Collections.unmodifiableList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.jcip.annotations.Immutable;

/**
 * OperatorImpl is the default implementation of the Operator interface
 */
@Immutable
public class OperatorImpl<RT> implements Operator<RT> {
    
    private final List<Class<?>> types;

    public OperatorImpl(Class<?> type) {
        this(Collections.<Class<?>> singletonList(type));
    }

    public OperatorImpl(Class<?>... types) {
        this(Arrays.<Class<?>> asList(types));
    }

    public OperatorImpl(List<Class<?>> types) {
        this.types = unmodifiableList(types);
    }

    @Override
    public List<Class<?>> getTypes() {
        return types;
    }
}