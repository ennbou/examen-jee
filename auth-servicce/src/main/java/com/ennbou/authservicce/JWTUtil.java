package com.ennbou.authservicce;

public class JWTUtil {
    public static final String SECRET="ennbou";
    public static final String PREFIX="Bearer ";
    public static final String AUTH_HEADER="Authorization";
    public static final long EXPIRE_ACCESS_TOKEN=5*60*1000;
    public static final long EXPIRE_REFRESH_TOKEN=30*60*1000;
}
