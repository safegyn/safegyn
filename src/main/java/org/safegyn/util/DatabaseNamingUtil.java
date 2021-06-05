package org.safegyn.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;


public class DatabaseNamingUtil extends PhysicalNamingStrategyStandardImpl {

    public static final DatabaseNamingUtil INSTANCE = new DatabaseNamingUtil();
    public static final String CAMEL_CASE_REGEX = "([a-z]+)([A-Z]+)";
    public static final String SNAKE_CASE_PATTERN = "$1\\_$2";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String formattedName = formatIdentifierText(super.toPhysicalTableName(name, context));
        return Identifier.toIdentifier(formattedName, name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String formattedName = formatIdentifierText(super.toPhysicalTableName(name, context));
        return Identifier.toIdentifier(formattedName, name.isQuoted());
    }

    private String formatIdentifierText(Identifier identifier) {
        String name = identifier.getText();
        return name.replaceAll(CAMEL_CASE_REGEX, SNAKE_CASE_PATTERN).toLowerCase();
    }

}