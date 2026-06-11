package api;

import com.intuit.karate.junit5.Karate;

class ApiTestRunner {
    @Karate.Test
    Karate testAuth() {
        return Karate.run("classpath:auth.feature");
    }
}