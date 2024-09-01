import { PageInfo } from './PageInfo'

export type Page<T> = {
  content: T[]
  page: PageInfo
}
