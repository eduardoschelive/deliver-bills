import type { Bill } from './Bill'

export type DetailedBill = Bill & {
  uuid: string
  correctedValue: number
  daysOverdue: number
}
