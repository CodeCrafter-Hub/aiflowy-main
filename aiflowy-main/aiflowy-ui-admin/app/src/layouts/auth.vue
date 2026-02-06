<script lang="ts" setup>
import { computed } from 'vue';

import { AuthPageLayout } from '@aiflowy/layouts';
import { preferences } from '@aiflowy/preferences';

const appName = computed(() => preferences.app.name);
const logo = computed(() => preferences.logo.source);
const logoDark = computed(() => preferences.logo.sourceDark);
const pageDescription = computed(() => preferences.auth.pageDescription);
const pageTitle = computed(() => preferences.auth.pageTitle);
const sloganImage = computed(() => preferences.auth.sloganImage);
</script>

<template>
  <AuthPageLayout
    :app-name="appName"
    :logo="logo"
    :logo-dark="logoDark"
    :page-description="pageDescription || $t('authentication.pageDesc')"
    :page-title="pageTitle || $t('authentication.pageTitle')"
    :slogan-image="sloganImage"
  >
    <!-- 自定义工具栏 -->
    <!-- <template #toolbar></template> -->
  </AuthPageLayout>
</template>
