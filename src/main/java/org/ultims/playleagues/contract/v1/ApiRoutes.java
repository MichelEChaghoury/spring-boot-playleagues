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

    public static class TEAMS {
        private static final String RESOURCE = "teams";

        public static final String GET_ALL = API_V1_ROOT_URL + "/" + RESOURCE;
        public static final String GET_BY_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/{id}";
        public static final String GET_BY_LEAGUE_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/search";
        public static final String CREATE = API_V1_ROOT_URL + "/" + RESOURCE;
        public static final String UPDATE_BY_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/{id}";
        public static final String DELETE_BY_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/{id}";
    }

    public static class MATCHES {
        private static final String RESOURCE = "matches";

        public static final String GET_ALL = API_V1_ROOT_URL + "/" + RESOURCE;
        public static final String GET_REPORTS = API_V1_ROOT_URL + "/" + RESOURCE + "/reports";
            public static final String GET_BY_YEAR = API_V1_ROOT_URL + "/" + RESOURCE + "/years" + "/{year}";
        public static final String GET_BY_TEAM_ID = API_V1_ROOT_URL + "/" + RESOURCE + "/teams" + "/{teamId}";
        public static final String GET_TOTAL_TEAMS_PER_LEAGUE = API_V1_ROOT_URL + "/" + RESOURCE + "/teams" + "/reports";
    }

}
