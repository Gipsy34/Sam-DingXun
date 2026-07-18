import vue from '@vitejs/plugin-vue'

import createAutoImport from './auto-import'
import createSvgIcon from './svg-icon'
import createCompression from './compression'
import createSetupExtend from './setup-extend'
import vueJsx from '@vitejs/plugin-vue-jsx'
import legacy from '@vitejs/plugin-legacy'
import viteCompression from 'vite-plugin-compression'
import { createHtmlPlugin } from "vite-plugin-html";
// import {viteMockServe} from "vite-plugin-mock"
// const localEnabled = process.env.USE_MOCK || false;
// const prodEnabled = process.env.USE_CHUNK_MOCK || false;

export default function createVitePlugins(viteEnv, isBuild = false) {
    const vitePlugins = [vue(),createHtmlPlugin({
        inject: {
            data: {
                //将环境变量 VITE_APP_TITLE 赋值给 title 方便 html页面使用 title 获取系统标题
                title: viteEnv.VITE_APP_TITLE
            },
        },
    }),]

    vitePlugins.push(createAutoImport())
    vitePlugins.push(createSetupExtend())
    vitePlugins.push(createSvgIcon(isBuild))
    // vitePlugins.push(legacy({
    //     targets: ['defaults', 'not IE 11', 'chrome 52'],
    //     additionalLegacyPolyfills:['regenerator-runtime/runtime'] // 面向IE11时需要此插件
    // }));
    vitePlugins.push(vueJsx());
    vitePlugins.push(viteCompression({
        verbose: true,
        disable: false,
        threshold: 10240,
        algorithm: 'gzip',
        ext: '.gz',
    }))
    isBuild && vitePlugins.push(...createCompression(viteEnv))
    // !isBuild && vitePlugins.push(viteMockServe({
    //     mockPath: "./src/server/mock",
    //     localEnabled: localEnabled, // 开发打包开关 true时打开mock  false关闭mock
    //     prodEnabled: prodEnabled,//prodEnabled, // 生产打包开关
    //     // 这样可以控制关闭mock的时候不让mock打包到最终代码内
    //     injectCode: `
    //  import { setupProdMockServer } from './mockProdServer';
    //  setupProdMockServer();
    // `,
    //     logger: false, //是否在控制台显示请求日志
    //     supportTs: false //打开后，可以读取 ts 文件模块 打开后将无法监视 .js 文件
    // }))

    return vitePlugins
}
