<template>
  <div class="not-found">
    <img src="@/assets/404-image.png" alt="Page Not Found" />
    <h1>Oops! The page you're looking for has gone off the radar.</h1>
  </div>
</template>

<script setup>

</script>

<style scoped>
.not-found {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  height: 300px;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

img {
  height: 800px;
  border-radius: 20px;
}
</style>