<script setup lang="ts">

  import { useModalStore } from "../stores/modal-store.ts";

  const modalStore = useModalStore();

  const { openStickyNoteModal } = modalStore;

</script>

<template>
  <button class="sticky-note" @click="openStickyNoteModal">Sticky Note</button>

</template>

<style scoped lang="scss">

.sticky-note{
  width: 100%;
}

</style>
