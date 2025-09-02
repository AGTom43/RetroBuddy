<script setup lang="ts">

import { useModalStore } from "../stores/modal-store.ts";
import { storeToRefs } from "pinia";
import { useBoardStore } from "../stores/board-store.ts";
import { computed, ref, toRef, watch } from "vue";

interface Props {
  id: string
  title: string
  body?: string
  positionX: number
  positionY: number
  colour: string
}

const props = defineProps<Props>()

let offsetX = 0;
let offsetY = 0;

let title = ref(props.title);
let body = ref(props.body);

let colour = ref(props.colour);

let debounceTimeout: number = 0;
let lastEmittedColour = props.colour;

watch(colour, (newColour) => {
  if (newColour === lastEmittedColour) return;

  if (debounceTimeout != 0) {
    clearTimeout(debounceTimeout);
  }

  debounceTimeout = setTimeout(() => {
    if (newColour !== lastEmittedColour) {
      lastEmittedColour = newColour;
      editStickyNote();
    }
  }, 500);
});

const modalStore = useModalStore();
const {stickyNoteToDeleteId} = storeToRefs(modalStore)
const {openStickyNoteDeletionModal} = modalStore;

const boardStore = useBoardStore()

const {activeBoardId, defaultColour} = storeToRefs(boardStore)
const {setAsDefault} = boardStore;

function openDeletionModal() {
  stickyNoteToDeleteId.value = props.id;
  openStickyNoteDeletionModal();
}

function dragStickyNote(event: DragEvent) {
  console.log(event.clientX, event.clientY);

  const posX = event.clientX - offsetX - 5;
  const posY = event.clientY - offsetY - 5;

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note/${props.id}/update`, {
    body: JSON.stringify({
      title: title.value,
      body: body.value,
      positionX: Math.round(posX / 10) * 10,
      positionY: Math.round(posY / 10) * 10,
      colour: colour.value,
    }),
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    }
  })
}

function editStickyNote(): void {

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/sticky-note/${props.id}/update`, {
    body: JSON.stringify({
      title: title.value,
      body: body.value,
      positionX: props.positionX,
      positionY: props.positionY,
      colour: colour.value,
    }),
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    }
  })
}

function startDragging(event: DragEvent) {
  console.log(event.offsetX, event.offsetY, "Offset");

  offsetX = event.offsetX;
  offsetY = event.offsetY;
}

function resetToDefault(): void {
  colour.value = defaultColour.value;
}

const iconColor = computed(() => {
  const hex = colour.value.replace('#', '')
  const r = parseInt(hex.substring(0, 2), 16)
  const g = parseInt(hex.substring(2, 4), 16)
  const b = parseInt(hex.substring(4, 6), 16)

  const luminance = (0.299 * r + 0.587 * g + 0.114 * b)

  return luminance > 186 ? 'black' : 'white'
})


function saveDefaultColour(): void {
  setAsDefault(colour.value)

  fetch(`http://localhost:8080/api/board/${activeBoardId.value}/edit`, {
    body: JSON.stringify({
      defaultColour: defaultColour.value
    }),
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    }
  })
}

</script>

<template>
  <div @dblclick.stop="" @dragstart="startDragging" @dragend="dragStickyNote" draggable="true"
       class="sticky-note" :class="colour" :style="`top: ${props.positionY}px; left: ${props.positionX}px;`">
    <v-icon-btn icon="mdi-trash-can" variant="plain" @dblclick.stop="" @click.stop="openDeletionModal"
                :color="iconColor"/>

    <v-menu>
      <template #activator="{ props }">
        <v-icon-btn
            icon="mdi-eyedropper"
            variant="plain"
            v-bind="props"
            :color="iconColor"
        />
      </template>

      <v-card>
        <v-card-item>
          <v-color-picker v-model="colour" @click.stop="" @change="editStickyNote" hide-inputs></v-color-picker>
        </v-card-item>

        <v-card-actions class="justify-center">
          <v-btn size="small" @click.stop="resetToDefault">
            Reset to default
          </v-btn>
          <v-btn size="small" @click.stop="saveDefaultColour">
            Set as default
          </v-btn>
        </v-card-actions>
      </v-card>

    </v-menu>

    <div class="body-text-field">
      <v-textarea @change="editStickyNote" label="Body" row-height="40" rows="4" auto-grow :bg-color="colour"
                  v-model="body" :color="iconColor" variant="plain" clearable draggable="false" @click:clear="editStickyNote"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
.sticky-note {
  border: 5px solid v-bind(colour);
  background-color: v-bind(colour);
  margin: 0.2rem;
  cursor: pointer;
  position: fixed;
  padding: 5px;
  filter: drop-shadow(3px 3px 3px #888);
  max-width: 300px;
  min-width: 300px;

  aspect-ratio: 1 / 1;
}

.body-text-field {
  margin: 0.5rem;
}

</style>
