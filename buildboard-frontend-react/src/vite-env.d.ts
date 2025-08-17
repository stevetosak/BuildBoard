/// <reference types="vite/client" />
/// <reference types="vite-plugin-svgr/client" />

// eslint-disable-next-line @typescript-eslint/no-empty-object-type
interface ViteTypeOptions {
}

interface ImportMetaEnv {
  readonly VITE_HOST: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}