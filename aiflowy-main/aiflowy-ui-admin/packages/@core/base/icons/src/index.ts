import antDesign from '@iconify/json/json/ant-design.json';
import ep from '@iconify/json/json/ep.json';
import mdi from '@iconify/json/json/mdi.json';
import {
  addCollection,
  addIcon,
  Icon as IconifyIcon,
  listIcons,
} from '@iconify/vue';

addCollection(antDesign);
addCollection(ep);
addCollection(mdi);

export * from './create-icon';

export * from './lucide';

export type { IconifyIcon as IconifyIconStructure } from '@iconify/vue';
export { addCollection, addIcon, IconifyIcon, listIcons };
