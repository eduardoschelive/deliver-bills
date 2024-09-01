import { PageRequest } from '@/model/PageRequest'
import { useState } from 'react'

export function usePagination(
  initialPage: number,
  initialItemsPerPage: number
) {
  const [pageRequest, setPageRequest] = useState<PageRequest>({
    page: initialPage,
    size: initialItemsPerPage,
  })

  const handlePage = (page: number) => {
    setPageRequest((prevPage) => ({
      ...prevPage,
      page,
    }))
  }

  const handlePageSize = (size: number) => {
    setPageRequest((prevPage) => ({
      ...prevPage,
      size,
      page: 1
    }))
  }

  return {
    pageRequest,
    handlePage,
    handlePageSize,
  }
}
