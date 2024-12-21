import type { CodegenConfig } from '@graphql-codegen/cli'
 
const config: CodegenConfig = {
  schema: 'http://localhost:8080/graphql',
  //documents: './src/**/*.ts',
  documents: './src/resources/schema/*.graphql',
  generates: {
    './src/app/core/modules/graphql/generated.ts': {
      plugins: ['typescript', 'typescript-operations', 'typescript-apollo-angular']
    }
  }
}
export default config