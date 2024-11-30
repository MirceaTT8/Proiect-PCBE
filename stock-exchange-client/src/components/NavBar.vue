<script>

import {useRouter} from "vue-router";


export default {
  name: 'NavBar',
  setup() {
    const router = useRouter();
    const links = router.options.routes.filter(route => route.name !== 'not-found');

    return {
      links,
    };
  },
};
</script>

<template>
  <nav class="navbar">
    <ul class="navbar-menu">
      <li class="navbar-item" v-for="link in links" :key="link.name">
        <router-link :to="link.path">{{ link.name }}</router-link>
      </li>
    </ul>
  </nav>
</template>


<style scoped>
.navbar {
  background-color: #333;
  padding: 1rem;
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
  transition: color 0.3s ease;
}

.navbar-item a:hover {
  color: #ffd700;
}
</style>
