package org.oop18.models;

/**
 * Factory used to create JDBCUserAdapter dynamically
 *
 * @author - Haribo
 */
public class JDBCUserAdapterFactory implements UserAdapterFactory {
    @Override
    public UserAdapter create() {
        return new JDBCUserAdapter();
    }
}
