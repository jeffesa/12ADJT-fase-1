package com.fiap.fase1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    private User user;
    private User sameUser;
    private User differentUser;

    @BeforeEach
    void setUp() throws Exception {
        user = new User("João Silva", "joao@email.com", "joaosilva", "hash", "Rua A, 1", UserType.CUSTOMER);
        sameUser = new User("João Silva", "joao@email.com", "joaosilva", "hash", "Rua A, 1", UserType.CUSTOMER);
        differentUser = new User("Maria", "maria@email.com", "maria", "hash", "Rua B, 2", UserType.RESTAURANT_OWNER);

        setId(user, 1L);
        setId(sameUser, 1L);
        setId(differentUser, 2L);
    }

    private void setId(User u, Long id) throws Exception {
        Field field = User.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(u, id);
    }

    @Test
    @DisplayName("Deve retornar true para o mesmo objeto")
    void equalsSameInstance() {
        assertEquals(user, user);
    }

    @Test
    @DisplayName("Deve retornar true para usuários com mesmo ID")
    void equalsSameId() {
        assertEquals(user, sameUser);
    }

    @Test
    @DisplayName("Deve retornar false para usuários com IDs diferentes")
    void equalsDifferentId() {
        assertNotEquals(user, differentUser);
    }

    @Test
    @DisplayName("Deve retornar false ao comparar com null")
    void equalsNull() {
        assertNotEquals(null, user);
    }

    @Test
    @DisplayName("Deve retornar false ao comparar com objeto de outra classe")
    void equalsDifferentClass() {
        assertNotEquals("string", user);
    }

    @Test
    @DisplayName("hashCode deve ser igual para usuários com mesmo ID")
    void hashCodeSameId() {
        assertEquals(user.hashCode(), sameUser.hashCode());
    }

    @Test
    @DisplayName("hashCode deve ser diferente para usuários com IDs diferentes")
    void hashCodeDifferentId() {
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }

    @Test
    @DisplayName("Construtor padrão deve criar instância não nula")
    void defaultConstructorCreatesInstance() {
        assertNotNull(new User());
    }

    @Test
    @DisplayName("updateLastModifiedDate deve definir timestamp ao persistir")
    void updateLastModifiedDateSetsTimestamp() throws Exception {
        User newUser = new User();
        Method method = User.class.getDeclaredMethod("updateLastModifiedDate");
        method.setAccessible(true);
        method.invoke(newUser);
        assertNotNull(newUser.getLastModifiedDate());
    }

    @Test
    @DisplayName("Getters e setters devem funcionar corretamente")
    void gettersAndSetters() {
        user.setName("Novo Nome");
        user.setEmail("novo@email.com");
        user.setLogin("novologin");
        user.setPassword("novasenha");
        user.setAddress("Nova Rua, 99");
        user.setType(UserType.RESTAURANT_OWNER);

        assertEquals("Novo Nome", user.getName());
        assertEquals("novo@email.com", user.getEmail());
        assertEquals("novologin", user.getLogin());
        assertEquals("novasenha", user.getPassword());
        assertEquals("Nova Rua, 99", user.getAddress());
        assertEquals(UserType.RESTAURANT_OWNER, user.getType());
    }
}
