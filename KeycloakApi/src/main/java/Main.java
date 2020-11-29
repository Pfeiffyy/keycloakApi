import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Arrays;

public class Main {

    private static final String SERVER_URL = "http://localhost:8280/auth";
    private static final String REALM = "ISABEL";
    private static final String USERNAME = "apiuser";
    private static final String PASSWORD = "beginn00";
    private static final String CLIENT_ID = "isabelapp";

    public static void main(String[] args) {

        Keycloak keycloak = KeycloakBuilder
                .builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .username(USERNAME)
                .password(PASSWORD)
                .clientId(CLIENT_ID)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue("12345678");

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername("lupin");
        userRepresentation.setFirstName("Remus");
        userRepresentation.setLastName("Lupin");
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Arrays.asList(credentialRepresentation));
        keycloak.realm(REALM).users().create(userRepresentation);

        UsersResource usersResource = keycloak.realm(REALM).users();
        UserRepresentation user = usersResource.search("testuser1").get(0);
        user.setEmail("lupin@hogwarts.co.uk");
        usersResource.get(user.getId()).update(user);
    }

}
