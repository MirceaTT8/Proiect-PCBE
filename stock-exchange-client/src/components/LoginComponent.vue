<template>
  <div class="popup" v-if="popupTrigger && !authenticated">
    <div class="login-popup-inner">
      <button class="popup-close" @click="togglePopup()">X</button>
      <h1>{{ isLoginForm ? 'Login' : 'Register' }}</h1>

      <div v-if="!isLoginForm" class="form-group">
        <label for="firstName">First Name</label>
        <input
            type="text"
            id="firstName"
            v-model="userData.firstName"
            required
        />
      </div>

      <div v-if="!isLoginForm" class="form-group">
        <label for="lastName">Last Name</label>
        <input
            type="text"
            id="lastName"
            v-model="userData.lastName"
            required
        />
      </div>

      <div class="form-group">
        <label for="email">Email</label>
        <input
            type="email"
            id="email"
            v-model="userData.email"
            required
        />
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input
            type="password"
            id="password"
            v-model="userData.password"
            required
        />
      </div>

      <div v-if="!isLoginForm" class="form-group">
        <label for="confirmPassword">Confirm Password</label>
        <input
            type="password"
            id="confirmPassword"
            v-model="userData.confirmPassword"
            required
        />
      </div>

      <div v-if="!isLoginForm" class="form-group">
        <label for="phoneNumber">Phone Number</label>
        <input
            type="tel"
            id="phoneNumber"
            v-model="userData.phoneNumber"
            required
        />
      </div>

      <div class="login-container">
        <button
            v-if="isLoginForm"
            class="login-button"
            @click="tryLogin()"
            :disabled="isSubmitting"
        >
          Log in
        </button>
        <button
            v-if="!isLoginForm"
            class="register-button"
            @click="tryRegister()"
            :disabled="isSubmitting"
        >
          Register
        </button>
        <button
            v-if="isLoginForm"
            class="go-to-register-button"
            @click="toggleLoginForm()"
        >
          Create an account
        </button>
        <button
            v-if="!isLoginForm"
            class="go-to-login-button"
            @click="toggleLoginForm()"
        >
          Go to login
        </button>
      </div>

      <!-- Notification Component -->
      <Notification
          v-if="notificationMessage"
          :message="notificationMessage"
          :type="notificationType"
      />
    </div>
  </div>
</template>

<script>
import {
  isAuthenticated,
  getUserByName,
  setCurrentUser,
  useNameState,
  loginUser,
  registerUser,
} from '@/services/userService';
import TextField from './TextField.vue';
import { ref, reactive } from 'vue';
import Notification from './Notification.vue';

export default {
  components: {
    TextField,
    Notification,
  },
  setup() {
    const notificationMessage = ref('');
    const notificationType = ref('');

    const userData = reactive({
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      phoneNumber: '',
    });

    const authenticated = isAuthenticated();
    const isSubmitting = ref(false);

    const tryLogin = async () => {
      try {
        isSubmitting.value = true;
        const currentUser = await loginUser(userData);

        if (currentUser !== undefined) {
          setCurrentUser(currentUser);
          const state = useNameState();
          state.username = currentUser.firstName;
          notificationMessage.value = 'Logged in successfully!';
          notificationType.value = 'success';
          togglePopup();
        } else {
          notificationMessage.value = 'Wrong email or password!';
          notificationType.value = 'error';
        }
      } catch (error) {
        notificationMessage.value = 'An error occurred during login!';
        notificationType.value = 'error';
        console.error('Login Error:', error);
      } finally {
        isSubmitting.value = false;
      }
    };

    const tryRegister = async () => {
      if (userData.password !== userData.confirmPassword) {
        notificationMessage.value = 'Passwords do not match!';
        notificationType.value = 'error';
        return;
      }

      try {
        isSubmitting.value = true;
        const currentUser = await registerUser(userData);

        if (currentUser !== undefined) {
          setCurrentUser(currentUser);
          const state = useNameState();
          state.username = userData.firstName;
          notificationMessage.value = 'User registered successfully!';
          notificationType.value = 'success';
          togglePopup();
        } else {
          notificationMessage.value = 'Error registering user!';
          notificationType.value = 'error';
        }
      } catch (error) {
        notificationMessage.value = 'An error occurred during registration!';
        notificationType.value = 'error';
        console.error('Registration Error:', error);
      } finally {
        isSubmitting.value = false;
      }
    };

    const popupTrigger = ref(true);

    const togglePopup = () => {
      popupTrigger.value = !popupTrigger.value;
      if (!popupTrigger.value) {
        resetForm();
      }
    };

    const isLoginForm = ref(true);

    const toggleLoginForm = () => {
      isLoginForm.value = !isLoginForm.value;
      resetForm();
    };

    const resetForm = () => {
      userData.firstName = '';
      userData.lastName = '';
      userData.email = '';
      userData.password = '';
      userData.confirmPassword = '';
      userData.phoneNumber = '';
      notificationMessage.value = '';
      notificationType.value = '';
    };

    return {
      userData,
      authenticated,
      tryLogin,
      tryRegister,
      popupTrigger,
      isLoginForm,
      togglePopup,
      toggleLoginForm,
      notificationMessage,
      notificationType,
      isSubmitting,
    };
  },
};
</script>

<style scoped>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 99;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-popup-inner {
  background-color: #ffffff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  position: relative;
}

.popup-close {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none;
  background: none;
  font-size: 1.2rem;
  cursor: pointer;
  color: #555;
}

h1 {
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: #555;
}

.form-group input {
  width: 300px;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

.login-container {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  width: 100%;
  margin-top: 1rem;
}

.login-button,
.register-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  border: none;
  border-radius: 15px;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover,
.register-button:hover {
  background-color: #0056b3;
}

.go-to-register-button,
.go-to-login-button {
  background: none;
  border: none;
  color: #007bff;
  cursor: pointer;
  font-size: 0.9rem;
}

.go-to-register-button:hover,
.go-to-login-button:hover {
  text-decoration: underline;
}
</style>