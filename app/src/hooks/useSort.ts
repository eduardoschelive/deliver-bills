import { SortParam } from '@/model/SortParam'
import { useState } from 'react'

export function useSort<T>(initialSort: SortParam<T>) {
  const [sort, setSort] = useState(initialSort)

  const handleSort = (column: keyof T) => {
    setSort((prevSort) => ({
      column,
      direction:
        prevSort.column === column
          ? prevSort.direction === 'asc'
            ? 'desc'
            : 'asc'
          : 'asc',
    }))
  }

  return {
    sort,
    handleSort,
  }
}
