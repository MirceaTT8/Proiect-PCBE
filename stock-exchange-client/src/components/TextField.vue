<template>
    <div class="text-field">
        <div class="label-container">
            <label>{{ labelTitle }}</label>
            <div v-if="isSelected" id="default-value">{{ defaultValue }}</div>
        </div>
        <input type="text" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)" required placeholder="Enter text here..." />
    </div>
</template>

<script setup>
import { ref } from 'vue';

defineProps({
    labelTitle: {
        type: String,
        default: 'Undefined label'
    },
    defaultValue: {
        type: String,
        default: 'Undefined default value'
    },
    modelValue: {
        type: String,
        required: true,
        default: 'Undefined input value'
    }
});

const isSelected = ref(false);
const toggleSelect = () => {
    isSelected.value = !isSelected.value;
}
const handleInput = (event) => {
    this.$emit('update', event.target.value);
}

</script>

<style scoped>
.text-field {
    padding: 15px;
}
label {
    color: black;
    display: inline-block;
    margin-left: 15px;
    font-weight: bold;
    max-width: 230px;
    text-transform: capitalize;
    font-size: large;
}
input {
    display: block;
    padding: 10px 6px;
    width: 100%;
    box-sizing: border-box;
    border: 3px solid green;
    border-radius: 15px;
    color: #555;
    outline: none;
    max-width: 500px;
}
input:focus {
    border: 3px solid lightgreen
}
.label-container {
    display: grid;
    grid-template-columns: 1fr 0.6fr;
    grid-gap: 10px;
    max-width: 500px;
    font-family: 'Roboto', sans-serif;
}
#default-value {
    max-width: 230px;
    text-align: end;
    margin-right: 15px;
}
    
</style>