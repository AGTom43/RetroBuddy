import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import { createPinia } from 'pinia'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import { VIconBtn } from 'vuetify/labs/VIconBtn'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import '@mdi/font/css/materialdesignicons.css'
import {
    VAppBar, VAppBarNavIcon, VAvatar,
    VBtn, VCard, VCardActions, VCardItem, VCol,
    VColorPicker,
    VDialog,
    VDivider, VIcon, VLayout,
    VList,
    VListItem,
    VMenu, VRow,
    VTextarea,
    VTextField
} from "vuetify/components";

const vuetify = createVuetify({
    components: {
        VBtn,
        VIconBtn,
        VTextField,
        VTextarea,
        VDialog,
        VMenu,
        VList,
        VListItem,
        VDivider,
        VColorPicker,
        VCard,
        VCardActions,
        VCardItem,
        VAppBar,
        VLayout,
        VRow,
        VCol,
        VAvatar,
        VAppBarNavIcon,
        VIcon,
        components
    },
    directives,
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: { mdi },
    },
})

import router from './router'

createApp(App)
    .use(createPinia())
    .use(vuetify)
    .use(router)
    .mount('#app')
