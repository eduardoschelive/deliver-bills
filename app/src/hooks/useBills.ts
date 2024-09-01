import type { Bill, DetailedBill, Page } from '@/model'
import { PageRequest } from '@/model/PageRequest'
import { SortParam } from '@/model/SortParam'
import { BillService } from '@/service/BillService'
import {
  keepPreviousData,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query'

const KEY = 'bills'

export function useBills(
  sort: SortParam<DetailedBill>,
  pageRequest: PageRequest,
  nameFilter: string
) {
  const { data, isFetching, isError, error } = useQuery<Page<DetailedBill>>({
    queryKey: [KEY, sort, pageRequest, nameFilter],
    queryFn: async () => {
      const { data } = await BillService.getBills(sort, pageRequest, nameFilter)
      return data
    },
    placeholderData: keepPreviousData,
  })

  return {
    data,
    isFetching,
    isError,
    error,
  }
}

export function useRemoveBill() {
  const queryClient = useQueryClient()
  const mutation = useMutation({
    mutationFn: async (uuid: string) => {
      await BillService.removeBill(uuid)
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [KEY],
      })
    },
  })

  return mutation
}

export const useUpsertBill = () => {
  const queryClient = useQueryClient()

  const mutationFunction = async (bill: Bill) => {
    if (bill.uuid) {
      return BillService.updateBill(bill)
    } else {
      return BillService.createBill(bill)
    }
  }

  const mutation = useMutation({
    mutationFn: async (bill: Bill) => {
      const response = mutationFunction(bill)
      return response
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [KEY],
      })
    },
  })

  return mutation
}
