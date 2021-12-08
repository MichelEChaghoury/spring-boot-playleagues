package org.ultims.playleagues.contract.v1;

public class ApiRoutes {

    private static final String API_V1_ROOT_URL = "/api/v1";

    public static class LEAGUES {
        private static final String RESOURCE = "leagues";

        public static final String GET_ALL = API_V1_ROOT_URL + "/" + RESOURCE;
        public static final String GET_BY_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/{id}";
        public static final String GET_BY_NAME = API_V1_ROOT_URL + "/" + RESOURCE + "/search";
        public static final String CREATE = API_V1_ROOT_URL + "/" + RESOURCE;
        public static final String DELETE_BY_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/{id}";
        public static final String UPDATE_BY_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/{id}";
    }

}
