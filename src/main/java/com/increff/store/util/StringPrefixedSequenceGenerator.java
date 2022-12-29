package com.increff.store.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Properties;

public class StringPrefixedSequenceGenerator extends SequenceStyleGenerator {
    public static final String Value_Prefix_Parameter = "valuePrefix";
    public static final String Value_Prefix_Default = "";
    private String valuePrefix;

    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%d";
    public String numberFormat;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
        return valuePrefix + String.format(numberFormat, super.generate(session, object));
    }

//    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws Exception
    {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(Value_Prefix_Parameter, params, Value_Prefix_Default);
        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT);
    }


}
