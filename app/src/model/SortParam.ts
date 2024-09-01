export type SortDirection = 'asc' | 'desc' | 'none'

export type SortParam<T> = {
  column: keyof T
  direction: SortDirection
}
