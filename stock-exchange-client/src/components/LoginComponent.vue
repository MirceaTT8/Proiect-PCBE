.<template>
    <div class="popup" v-if="popupTrigger && !authenticated">
          <div class="login-popup-inner">
              <button class="popup-close" @click="togglePopup()">X</button>
              <TextField v-if="!isLoginForm" label-title="First Name" default-value="" v-model="userData.firstName"/>
              <TextField v-if="!isLoginForm" label-title="Last Name" default-value="" v-model="userData.lastName"/>
              <TextField label-title="Email" default-value="" v-model="userData.email"/>
              <TextField label-title="Password" default-value="" v-model="userData.password"/>
              <TextField v-if="!isLoginForm" label-title="Phone Number" default-value="" v-model="userData.phoneNumber"/>
              <div class="login-container">
                  <button v-if="isLoginForm" class="login-button" @click="tryLogin()">Log in</button>
                  <button v-if="!isLoginForm" class="register-button" @click="tryRegister()">Register</button>
                  <button v-if="isLoginForm" class="go-to-register-button" @click="toggleLoginForm()">Create an account</button>
                  <button v-if="!isLoginForm" class="go-to-login-button" @click="toggleLoginForm()">Go to login</button>
              </div>
              <Notification v-if="notificationMessage"
                  :message="notificationMessage"
                  :type="notificationType"
              />
          </div>
      </div>
  </template>
  
  <script>
  import { isAuthenticated, getUserByName, setCurrentUser, useNameState, loginUser, registerUser } from '@/services/userService';
  import TextField from './TextField.vue';
  import {ref,reactive} from 'vue';
  import Notification from './Notification.vue';
  import { fetchDefaultTradingStock, setDefaultTradingStock } from '@/services/stockService';
  
  export default {
      components: {
          TextField,
          Notification
      },
      setup() {
          const notificationMessage = ref('');
          const notificationType = ref('');
  
          const userData = reactive({
              firstName: '',
              lastName: '',
              email: '',
              password: '',
              phoneNumber: '',
          })
          const authenticated = isAuthenticated();
  
          const tryLogin = async () => {
              const currentUser = ref([]);
              currentUser.value = await loginUser(userData)
              
              if (currentUser.value !== undefined) {
                  setCurrentUser(currentUser.value);
                  //const defaultTradingStock = await fetchDefaultTradingStock();
                  //setDefaultTradingStock(defaultTradingStock.value);
                  const state = useNameState();
                  state.username = currentUser.value.firstName;
                  notificationMessage.value = 'Logged in successfully!';
                  notificationType.value = 'success';
                  togglePopup();
              }
              else {
                  notificationMessage.value = 'Wrong first or last name!';
                  notificationType.value = 'error';
              }
          }
  
          const tryRegister = async () => {
              const currentUser = ref([]);
              currentUser.value = await registerUser(userData)
              
              if (currentUser.value !== undefined) {
                  setCurrentUser(currentUser.value);
                  //const defaultTradingStock = await fetchDefaultTradingStock();
                  //setDefaultTradingStock(defaultTradingStock.value);
                  const state = useNameState();
                  state.username = userData.firstName;
                  notificationMessage.value = 'User registered successfully!';
                  notificationType.value = 'success';
                  togglePopup();
              }
              else {
                  notificationMessage.value = 'Error registering user!';
                  notificationType.value = 'error';
              }
          }

          const popupTrigger = ref(true);
  
          const togglePopup = (trigger) => {
              popupTrigger.value = !popupTrigger.value;
          }

          const isLoginForm = ref(true)

          const toggleLoginForm = (trigger) => {
              isLoginForm.value = !isLoginForm.value;
          }
  
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
              notificationType
          }
      }
  }
  </script>
  
  <style scoped>
  .popup {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      z-index: 99;
      background-color: rgba(0,0,0,0.2);
      display: flex;
      align-items: center;
      justify-content: center;
  }
  
  .login-popup-inner {
      background: white;
      padding: 32px;
      width: 500px;
      height: 500px;
      border-radius: 15px;
  }
  
  .popup-close {
      float: right;
  }
  
  .login-container{
      display: flex;
      justify-content: center;
  }
  
  .login-button {
    margin-top: 0rem;
    margin-bottom: 0.4rem;
    padding: 0.5rem 1rem;
    border: 1px solid #007bff;
    background-color: #fff;
    color: #007bff;
    cursor: pointer;
    border-radius: 4px;
    font-weight: bold;
    height: 50px;
    width: 100px;
  }

  .go-to-login-button {
    margin-top: 0rem;
    margin-bottom: 0.4rem;
    padding: 0.5rem 1rem;
    border: 1px solid #007bff;
    background-color: #fff;
    color: #007bff;
    cursor: pointer;
    border-radius: 4px;
    font-weight: bold;
    height: 50px;
    width: 100px;
  }
  
  .login-button.active {
    background-color: #007bff;
    color: #fff;
  }
  
  .login-button:hover {
    background-color: #0056b3;
    color: #fff;
  }

  .go-to-login-button.active {
    background-color: #007bff;
    color: #fff;
  }
  
  .go-to-login-button:hover {
    background-color: #0056b3;
    color: #fff;
  }
  
  .register-button {
    margin-top: 0rem;
    margin-bottom: 0.4rem;
    padding: 0.5rem 1rem;
    border: 1px solid #007bff;
    background-color: #fff;
    color: #007bff;
    cursor: pointer;
    border-radius: 4px;
    font-weight: bold;
    height: 50px;
    width: 100px;
  }

  .go-to-register-button {
    margin-top: 0rem;
    margin-bottom: 0.4rem;
    padding: 0.5rem 1rem;
    border: 1px solid #007bff;
    background-color: #fff;
    color: #007bff;
    cursor: pointer;
    border-radius: 4px;
    font-weight: bold;
    height: 50px;
    width: 100px;
  }
  
  .register-button.active {
    background-color: #007bff;
    color: #fff;
  }
  
  .register-button:hover {
    background-color: #0056b3;
    color: #fff;
  }

  .go-to-register-button.active {
    background-color: #007bff;
    color: #fff;
  }
  
  .go-to-register-button:hover {
    background-color: #0056b3;
    color: #fff;
  }
  </style>