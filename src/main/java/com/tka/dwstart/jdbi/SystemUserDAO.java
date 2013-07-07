package com.tka.dwstart.jdbi;

import java.util.Date;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

@RegisterMapper({SystemUserResultMapper.class, TokenResultMapper.class })
public interface SystemUserDAO extends Transactional<SystemUserDAO> {

    @SqlUpdate("insert into systemuser (email, password) values (:email, :password)")
    @GetGeneratedKeys
    long create(@Bind("email") String email, @Bind("password") String password);

    @SqlUpdate("delete from systemuser where id = :id")
    void delete(@Bind("id") long id);

    @SqlUpdate("update systemuser set lastLogin = :lastlogin where id = :id")
    void updateLastLogin(@Bind("id")long id, @Bind("lastlogin") Date lastLogin);

    @SqlUpdate("update systemuser set password = :password where id = :id")
    void updatePassword(@Bind("id") long id, @Bind("password") String password);

    @SqlUpdate("update systemuser set emailverified = :emailverified where id = :id")
    void verifyEmail(@Bind("id") long id, @Bind("emailverified") Date emailVerified);

    @SqlQuery("select id, created, email, password, lastLogin, emailverified from systemuser where id = :id")
    SystemUser getById(@Bind("id") long id);

    @SqlQuery("select id, created, email, password, lastLogin, emailverified from systemuser where email = :email")
    SystemUser getByEmail(@Bind("email") String email);

    @SqlQuery("select count(id) = 1 from systemuser where email = :email")
    boolean exists(@Bind("email") String email);

    @SqlUpdate("insert into token (tokentype, userid, token, expires) values (:tokentype, :userid,  :token, :expires)")
    void createToken(@Bind("userid") long userId,
                     @Bind("tokentype") String tokenType,
                     @Bind("token") String token,
                     @Bind("expires") Date expires);

    @SqlQuery("select id, userid, tokentype, token, created, expires from token where token = :token")
    Token getToken(@Bind("token") String token);

    @SqlUpdate("update token set expires = :expires where token = :token")
    void updateTokenExpireDate(@Bind("token") String token, @Bind("expires") Date expires);

}
