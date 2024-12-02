<template>
  <div class="profile-view">
    <h1>User Profile</h1>
    <Profile />
  </div>
</template>

<script>
import Profile from '@/components/Profile.vue';

export default {
  name: 'ProfileView',
  components: {
    Profile,
  },
};
</script>

<style scoped>
.profile-view {
  text-align: center;
  padding: 2rem;
}

h1 {
  margin-bottom: 1.5rem;
  color: #333;
}
</style>
