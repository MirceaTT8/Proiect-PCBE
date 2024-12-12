<template>
  <nav class="navbar">
    <div class="logo">Stock Exchange Admin Panel</div>
    <ul class="nav-links">
      <li>
        <router-link to="/portfolios" class="nav-link" active-class="active">Portfolios</router-link>
      </li>
      <li>
        <router-link to="/orders" class="nav-link" active-class="active">Orders</router-link>
      </li>
      <li>
        <router-link to="/users" class="nav-link" active-class="active">Users</router-link>
      </li>
      <li>
        <router-link to="/stocks" class="nav-link" active-class="active">Stocks</router-link>
      </li>
    </ul>
  </nav>
</template>

<script setup>
import {ref} from 'vue';

const isOpen = ref(false);

function toggleMenu() {
  isOpen.value = !isOpen.value;
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #333;
  color: white;
  padding: 10px 20px;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.nav-links {
  list-style: none;
  display: flex;
  gap: 15px;
}

.nav-links li a {
  color: white;
  text-decoration: none;
  transition: color 0.3s;
}

.nav-links li a:hover {
  color: #4caf50;
}

@media (max-width: 600px) {
  .nav-links {
    display: block;
    position: absolute;
    width: 100%;
    background-color: #333;
    top: 48px;
    left: 0;
    margin: 0;
    padding: 10px;
    visibility: hidden;
    opacity: 0;
    transition: opacity 0.3s;
  }

  .nav-links.active {
    visibility: visible;
    opacity: 1;
  }
}
</style>