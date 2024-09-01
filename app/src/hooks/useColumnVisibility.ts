import { Selection } from '@nextui-org/react'
import { useState } from 'react'

export function useColumnVisibility(initialVisibleColumns: string[]) {
  const [visibleColumns, setVisibleColumns] = useState<Selection>(
    new Set(initialVisibleColumns)
  )

  return {
    visibleColumns,
    setVisibleColumns,
  }
}
