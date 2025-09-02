import { defineStore } from "pinia";
import { ref } from "vue";

export const useModalStore = defineStore('modalStore', () => {
    const stickyNoteModalStatus = ref(false)
    const stickyNoteDeletionModalStatus = ref(false)
    const stickyNoteEditModalStatus = ref(false)
    const stickyNoteToDeleteId = ref<string | null>(null)
    const stickyNoteToEditId = ref<string | null>(null)
    const stickyNoteCreationPositionX = ref(0)
    const stickyNoteCreationPositionY = ref(0)
    const stickyNoteModalTitle = ref<string | null>(null)
    const stickyNoteModalBody = ref<string | null>(null)

    function openStickyNoteModal() {
        stickyNoteModalStatus.value = true
    }

    function closeStickyNoteModal() {
        stickyNoteCreationPositionX.value = 0;
        stickyNoteCreationPositionY.value = 0;
        stickyNoteModalStatus.value = false
    }

    function openStickyNoteDeletionModal() {
        stickyNoteDeletionModalStatus.value = true
    }

    function closeStickyNoteDeletionModal() {
        stickyNoteDeletionModalStatus.value = false
    }

    function openStickyNoteEditModal() {
        stickyNoteEditModalStatus.value = true
    }

    function closeStickyNoteEditModal() {
        stickyNoteEditModalStatus.value = false
    }

    return {
        stickyNoteModalStatus, openStickyNoteModal, closeStickyNoteModal,
        stickyNoteDeletionModalStatus, openStickyNoteDeletionModal, closeStickyNoteDeletionModal,
        stickyNoteEditModalStatus, openStickyNoteEditModal, closeStickyNoteEditModal,
        stickyNoteToDeleteId, stickyNoteToEditId, stickyNoteCreationPositionX, stickyNoteCreationPositionY,
        stickyNoteModalTitle, stickyNoteModalBody
    }
})
