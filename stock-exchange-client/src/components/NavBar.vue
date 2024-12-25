<script>
import { setCurrentUser, useNameState } from "@/services/userService";
import { useRouter } from "vue-router";

export default {
  name: "NavBar",
  setup() {
    const router = useRouter();
    const links = router.options.routes.filter((route) => route.name !== "not-found");
    
    const nameState = useNameState();

    const navigateToDashboard = () => {
      router.push({ name: "Dashboard" }); // Navigate to the DashboardView route
    };

    const navigateToProfile = () => {
      router.push({ name: "Profile" }); // Navigate to the ProfileView route
    };

    const logOut = () => {
      setCurrentUser({});
      nameState.username = "No Account";
      navigateToDashboard();
    }

    return {
      links,
      navigateToDashboard,
      navigateToProfile,
      logOut,
      nameState
    };
  },
};
</script>



<template>
  <nav class="navbar">
    <ul class="navbar-menu">
      <li class="navbar-item">
        <router-link to="/" class="navbar-link">Dashboard</router-link>
      </li>
      <li class="navbar-item">
        <router-link to="/portfolio" class="navbar-link">Portfolio</router-link>
      </li>
      <li class="navbar-item">
        <router-link to="/transactions" class="navbar-link">Transactions</router-link>
      </li>
      <li class="navbar-item">
        <router-link to="/orders" class="navbar-link">Orders</router-link>
      </li>
    </ul>
    <div class="navbar-user">
      <div class="dropdown">
        <!-- Clicking on this navigates to the Dashboard -->
        <button class="dropdown-button" @click="navigateToDashboard">
          {{ nameState.username }}
        </button>
        <!-- Dropdown content -->
        <div class="dropdown-content">
          <p @click="navigateToProfile">Manage Account</p>
        </div>
      </div>
    </div>
  </nav>
</template>



<style scoped>
.navbar {
  background-color: #333;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navbar-menu {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  gap: 1rem;
}

.navbar-item a {
  text-decoration: none;
  color: white;
  transition: color 0.3s ease, cursor 0.3s ease;
}

.navbar-item a:hover {
  color: #aaa;
  cursor: pointer;
}

.navbar-user {
  position: relative;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-button {
  background-color: #444;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.dropdown-button:hover {
  background-color: #555;
  cursor: pointer;
}

.dropdown-content {
  display: none;
  position: absolute;
  right: 0;
  background-color: #444;
  min-width: 150px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content p {
  color: white;
  padding: 0.5rem 1rem;
  margin: 0;
  cursor: pointer;
}

.dropdown-content p:hover {
  background-color: #555;
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>
