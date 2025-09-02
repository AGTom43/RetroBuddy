import { defineStore } from "pinia";
import { reactive, ref } from "vue";

const defaultColour = ref("#f3c446");

export type JsonCreateBoardResponse = {
    boardId: string;
}

export interface StickyNote {
    id: string;
    title: string;
    body?: string;
    positionX: number;
    positionY: number;
    colour: string;
}

export const useBoardStore = defineStore("boardStore", () => {
    const activeBoardId = ref<string>()
    const isHomePage = ref<boolean>(true)
    const stickyNotesOnBoard = ref<StickyNote[]>([])

    async function createBoard() {
        return fetch("http://localhost:8080/api/board/create", {
            method: "POST",
        })
            .then(value => value.json())
            .then(value => value as JsonCreateBoardResponse)
    }

    function setActiveBoard(boardId: string) {
        activeBoardId.value = boardId;
    }

    function setAsDefault(colour: string) : void {
        defaultColour.value = colour;
    }

    function setIsHomePage(isHome: boolean) : void {
        isHomePage.value = isHome;
    }

    return {
        createBoard,
        setActiveBoard,
        setAsDefault,
        setIsHomePage,
        activeBoardId,
        stickyNotesOnBoard,
        defaultColour,
        isHomePage,
    }
})


