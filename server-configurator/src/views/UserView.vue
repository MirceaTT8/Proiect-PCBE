<script setup>
import { onMounted, reactive, ref, watch } from 'vue';
import { fetchUserById, createUser, updateUser, deleteUser } from '@/services/userService.js';
import Notification from '@/components/Notification.vue';

// Reactive user object including the id field
const user = reactive({
  id: '',
  firstName: '',
  lastName: '',
  email: '',
  phoneNumber: '',
});

// State variables
const isSubmitting = ref(false);
const message = ref('');
const messageType = ref('');

const resetForm = () => {
  user.firstName = '';
  user.lastName = '';
  user.email = '';
  user.phoneNumber = '';
  user.id = '';
};

const fetchUserData = async (id) => {
  try {
    if (id) {
      const userData = await fetchUserById(id);
      if (userData) {
        Object.assign(user, userData);
        message.value = '';
      } else {
        resetForm();
        message.value = 'User not found.';
        messageType.value = 'error';
      }
    } else {
      resetForm();
      message.value = '';
    }
  } catch (error) {
    resetForm();
    message.value = `Error fetching user: ${error.message}`;
    messageType.value = 'error';
  }
};

watch(() => user.id, (newId) => {
  if (newId) {
    fetchUserData(newId);
  } else {
    resetForm();
  }
});

const handleCreate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    const newUser = { ...user };
    delete newUser.id;
    const createdUser = await createUser(newUser);
    message.value = 'User created successfully!';
    messageType.value = 'success';

    user.id = createdUser.id;
  } catch (error) {
    message.value = `Error creating user: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

const handleUpdate = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await updateUser(user);
    message.value = 'User updated successfully!';
    messageType.value = 'success';
  } catch (error) {
    message.value = `Error updating user: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};

const handleDelete = async () => {
  try {
    isSubmitting.value = true;
    message.value = '';
    await deleteUser(user.id);
    message.value = 'User deleted successfully!';
    messageType.value = 'success';
    resetForm();
  } catch (error) {
    message.value = `Error deleting user: ${error.message}`;
    messageType.value = 'error';
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<template>
  <div class="user-management-container">
    <div class="user-management-form">
      <h1>User Management</h1>
      <form @submit.prevent="">
        <div class="form-group">
          <label for="id">User ID</label>
          <input
              id="id"
              v-model="user.id"
              type="text"
              placeholder="Enter user ID to load data or leave empty to create new user"
          />
        </div>
        <div class="form-group">
          <label for="firstName">First Name</label>
          <input id="firstName" v-model="user.firstName" type="text" required />
        </div>
        <div class="form-group">
          <label for="lastName">Last Name</label>
          <input id="lastName" v-model="user.lastName" type="text" required />
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" v-model="user.email" type="email" required />
        </div>
        <div class="form-group">
          <label for="phone">Phone Number</label>
          <input id="phone" v-model="user.phoneNumber" type="tel" required />
        </div>
        <div class="button-group">
          <button
              v-if="!user.id"
              type="button"
              class="create-button"
              @click="handleCreate"
              :disabled="isSubmitting.value"
          >
            Create
          </button>
          <div v-else>
            <button
                type="button"
                class="update-button"
                @click="handleUpdate"
                :disabled="isSubmitting.value"
            >
              Update
            </button>
            <button
                type="button"
                class="delete-button"
                @click="handleDelete"
                :disabled="isSubmitting.value"
            >
              Delete
            </button>
          </div>
        </div>
      </form>
    </div>
    <Notification
        v-if="message"
        :message="message"
        :type="messageType"
    />
  </div>
</template>

<style scoped>
.user-management-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  padding: 120px;
  position: relative;
  border-radius: 20px;
}

.user-management-form {
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

.user-management-form h1 {
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

.button-group {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 2rem;
}

.create-button,
.update-button,
.delete-button {
  width: 48%;
  padding: 0.75rem;
  border: none;
  border-radius: 15px;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.create-button {
  background-color: #28a745;
}

.update-button {
  background-color: #007bff;
}

.delete-button {
  background-color: #dc3545;
}

.create-button:hover {
  background-color: #218838;
}

.update-button:hover {
  background-color: #0056b3;
}

.delete-button:hover {
  background-color: #c82333;
}
</style>