<template>
  <div v-if="authenticated" class="profile-view">
    <h1>User Profile</h1>
    <Profile />
    <button @click="logOut">Log out</button>
  </div>
  <div v-else class="login-container">
    <h1>You are not logged in</h1>
    <button @click="navigateToDashboard">Log in</button>
  </div>
</template>

<script>
import Profile from '@/components/Profile.vue';
import { isAuthenticated, setCurrentUser, useNameState } from '@/services/userService';
import { useRouter } from 'vue-router';

export default {
  name: 'ProfileView',
  components: {
    Profile,
  },
  setup(){
    const router = useRouter();
    const links = router.options.routes.filter((route) => route.name !== "not-found");
    
    const authenticated = isAuthenticated();

    const nameState = useNameState();

    const navigateToDashboard = () => {
      router.push({ name: "Dashboard" }); // Navigate to the DashboardView route
    };

    const logOut = () => {
      setCurrentUser({});
      nameState.username = "No Account";
      navigateToDashboard();
    }

    return {
      links,
      navigateToDashboard,
      authenticated,
      nameState,
      logOut
    }
  }
};
</script>

<style scoped>
.profile-view {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem;
}

h1 {
  margin-bottom: 0;
  color: #333;
  padding: 25px;
}

.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

button {
  margin-top: 0.5rem;
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

button.active {
  background-color: #007bff;
  color: #fff;
}

button:hover {
  background-color: #0056b3;
  color: #fff;
}
</style>
