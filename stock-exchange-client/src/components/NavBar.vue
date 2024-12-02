<script>
import { useRouter } from "vue-router";

export default {
  name: "NavBar",
  setup() {
    const router = useRouter();
    const links = router.options.routes.filter(route => route.name !== "not-found");
    const username = "JohnDoe"; // Default username displayed

    const navigateToProfile = () => {
      router.push({ name: "ProfileView" }); // Navigates to the ProfileView route
    };

    return {
      links,
      username,
      navigateToProfile,
    };
  },
};
</script>

<template>
  <nav class="navbar">
    <ul class="navbar-menu">
      <li class="navbar-item" v-for="link in links" :key="link.name">
        <router-link :to="link.path" class="navbar-link">{{ link.name }}</router-link>
      </li>
    </ul>
    <div class="navbar-user">
      <div class="dropdown">
        <button class="dropdown-button">
          {{ username }}
        </button>
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
