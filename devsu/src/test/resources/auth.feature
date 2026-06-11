Feature: Tests API para vaidaciones de demoblaze.com

  Background:
    * url 'https://api.demoblaze.com'
    * def randomString = function() { return java.util.UUID.randomUUID().toString().substring(0,8) }
    * def toBase64 = function(s) { return java.util.Base64.getEncoder().encodeToString(s.getBytes()) }
    * def new_username = 'alejandro_devsu_' + randomString()
    * def new_password = 'Alejandro1234XYZ'
    * def new_coded_password = toBase64(new_password)
    * def exist_username = 'alexus86'
    * def plain_password = 'alexus'
    * def exist_password = toBase64(plain_password)

  Scenario: Crear nuevo usuario - registro exitoso
    Given path 'signup'
    And request { username: '#(new_username)', password: '#(new_password)' }
    When method post
    Then status 200
    * print 'Respuesta completa: ', response

  Scenario: Crear usuario ya existente - registro fallido
    Given path 'signup'
    And request { username: '#(exist_username)', password: '#(exist_password)' }
    When method post
    Then status 200
    And match response == {"errorMessage": "This user already exist."}

  Scenario: Login con datos correctos
    Given path 'login'
    And request { username: '#(exist_username)', password: '#(exist_password)' }
    When method post
    Then status 200
    And match response contains 'Auth_token'

  Scenario: Login con credenciales incorrectas - Password incorrecto
    Given path 'login'
    And request { username: '#(exist_username)', password: 'new_password' }
    When method post
    Then status 200
    And match response.errorMessage == "Wrong password."
    
Scenario: Login con credenciales incorrectas - username incorrecto
    Given path 'login'
    And request { username: 'usuario_inexistente', password: '#(new_coded_password)' }
    When method post
    Then status 200
    And match response.errorMessage == "User does not exist."
