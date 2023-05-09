package com.example.clothual.Util;

public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    /*
    public IUserRepository getUserRepository(Application application) {
        SharePreferenceReadWrite sharedPreferencesUtil = new SharePreferenceReadWrite(application);

        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationRemoteDataSource();


       // DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);


        return new AuthenticationRepository(application, userRemoteAuthenticationDataSource);
    }

     */

}
