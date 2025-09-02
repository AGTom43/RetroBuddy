<script setup lang="ts">

import { useModalStore } from "../stores/modal-store.ts";
import { storeToRefs } from "pinia";
import { useBoardStore } from "../stores/board-store.ts";

const modalStore = useModalStore();
const boardStore = useBoardStore();

const { activeBoardId } = storeToRefs(boardStore)

const { stickyNoteDeletionModalStatus, stickyNoteToDeleteId } = storeToRefs(modalStore)
const { closeStickyNoteDeletionModal } = modalStore;

function deleteStickyNote() {
  console.log(stickyNoteToDeleteId)

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note/${stickyNoteToDeleteId.value}/delete`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    }
  })

  closeStickyNoteDeletionModal();
}

</script>

<template>
  <div class="text-center pa-4">
    <v-dialog
        v-model="stickyNoteDeletionModalStatus"
        max-width="400"
        persistent
    >
      <v-card
          prepend-icon="mdi-trash-can"
          text="The sticky note will be permanently removed and you will not be able to retrieve it. Are you sure you want to delete it?"
          title="Delete this sticky note?"
      >
        <template v-slot:actions>
          <v-spacer></v-spacer>

          <v-btn @click="deleteStickyNote" color="red">
            Delete
          </v-btn>

          <v-btn @click="stickyNoteDeletionModalStatus = false">
            Cancel
          </v-btn>
        </template>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped lang="scss">
dialog {
  padding: 0;
}

h1 {
  background-color: #efefef;
  margin: 0;
  padding: 1rem;
  border-bottom: 2px solid black;
  margin-bottom: 1rem;
  text-align: center;
  font-size: 1.25rem;
}

.button__container {
  text-align: center;
  padding: 0 1rem 1rem 0;
}

.button{
  padding: 0.5rem 1rem;

  &:not(:last-child){
    margin-right: 0.25rem;
  }
}

</style>
