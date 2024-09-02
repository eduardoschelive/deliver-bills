import { http } from '@/lib'
import { Bill, DetailedBill, Page } from '@/model'
import { PageRequest } from '@/model/PageRequest'
import { SortParam } from '@/model/SortParam'
import { AxiosResponse } from 'axios'

const ENDPOINT = '/v1/bills'

const mapBillDates = (bill: Bill): Bill => ({
  ...bill,
  dueDate: new Date(bill.dueDate),
  paymentDate: new Date(bill.paymentDate),
})

const createBill = async (bill: Bill): Promise<AxiosResponse<void>> => {
  return await http.post(ENDPOINT, bill)
}

const getBills = async (
  sort: SortParam<DetailedBill>,
  pageRequest: PageRequest,
  nameFilter: string
): Promise<AxiosResponse<Page<DetailedBill>>> => {
  const { page, size } = pageRequest

  const newSort = {
    [`${sort.column}_sort`]: sort.direction,
  }

  const description = nameFilter ? { ['name_lk-i']: nameFilter } : {}

  const response = await http.get(ENDPOINT, {
    params: {
      ...newSort,
      page,
      size,
      ...description,
    },
  })
  response.data.content = response.data.content.map(mapBillDates)
  return response
}

export const removeBill = async (
  uuid: string
): Promise<AxiosResponse<void>> => {
  return await http.delete(`${ENDPOINT}/${uuid}`)
}

export const updateBill = async (bill: Bill): Promise<AxiosResponse<void>> => {
  return await http.put(`${ENDPOINT}/${bill.uuid}`, bill)
}

export const BillService = {
  createBill,
  getBills,
  updateBill,
  removeBill,
}
