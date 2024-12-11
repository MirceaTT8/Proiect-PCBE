
<script>
export default {
  name: "Profile",
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
    };
  },
  methods: {
    updateProfile() {
      // Logic to handle profile update (e.g., send data to API)
      console.log("Profile Updated:", this.firstName, this.lastName, this.email, this.phoneNumber);
      alert("Profile successfully updated!");
    },
  },
};
</script>

<template>
  <div class="profile-container">
    <div class="profile-form">
      <h1>Update Profile</h1>
      <form @submit.prevent="updateProfile">
        <div class="form-group">
          <label for="firstName">First Name</label>
          <input id="firstName" v-model="firstName" type="text" required />
        </div>
        <div class="form-group">
          <label for="lastName">Last Name</label>
          <input id="lastName" v-model="lastName" type="text" required />
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" v-model="email" type="email" required />
        </div>
        <div class="form-group">
          <label for="phone">Phone Number</label>
          <input id="phone" v-model="phoneNumber" type="tel" required />
        </div>
        <button type="submit" class="update-button">Update</button>
      </form>
    </div>
  </div>
</template>


<style scoped>
.profile-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  padding: 120px;
  border-radius: 20px;
}

.profile-form {
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
}

.profile-form h1 {
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

.update-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  border: none;
  border-radius: 15px;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 2rem;
}

.update-button:hover {
  background-color: #0056b3;
}
</style>
