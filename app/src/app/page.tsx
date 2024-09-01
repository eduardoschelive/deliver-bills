'use client'

import { AddBillButton } from '@/components/AddBillButton'
import { ChevronIcon } from '@/components/ChevronIcon'
import { ColumnsSelector } from '@/components/ColumnsSelector'
import { PageSizeDropdown } from '@/components/PageSizeDropdown'
import { UpsertBillModal } from '@/components/UpserBillModal'
import {
  useBills,
  useColumnVisibility,
  useDebounce,
  usePagination,
  useRemoveBill,
  useSort,
  useUpsertBill,
  useUpsertBillDisclousure,
} from '@/hooks'
import { Bill, DetailedBill } from '@/model'
import { CurrencyUtils, DateUtils } from '@/utils'
import {
  Button,
  Input,
  Pagination,
  Spinner,
  Table,
  TableBody,
  TableCell,
  TableColumn,
  TableHeader,
  TableRow,
} from '@nextui-org/react'
import { useMemo, useState } from 'react'

const INITIAL_VISIBLE_COLUMNS: Array<keyof DetailedBill> = [
  'name',
  'originalValue',
  'correctedValue',
  'daysOverdue',
  'paymentDate',
]

const COLUMNS = [
  { key: 'name', label: 'Nome', sortable: true },
  { key: 'originalValue', label: 'Valor Original', sortable: true },
  { key: 'correctedValue', label: 'Valor Corrigido', sortable: true },
  { key: 'daysOverdue', label: 'Dias em Atraso', sortable: true },
  { key: 'dueDate', label: 'Data de Vencimento', sortable: true },
  { key: 'paymentDate', label: 'Data de Pagamento', sortable: true },
]

const OPTIONS_COLUMN = { key: 'options', label: 'Opções', sortable: false }

type AvailableSortColumns = Omit<DetailedBill, 'uuid'>

const PAGE_SIZE_OPTIONS = [5, 10, 15, 20, 50]

export default function Home() {
  const [filter, setFilter] = useState('')
  const debouncedValue = useDebounce(filter, 200)

  const { handleSort, sort } = useSort<AvailableSortColumns>({
    column: 'name',
    direction: 'asc',
  })

  const { pageRequest, handlePage, handlePageSize } = usePagination(
    1,
    PAGE_SIZE_OPTIONS[0]
  )

  const { data, isFetching } = useBills(sort, pageRequest, debouncedValue)
  const { mutateAsync: removeBill } = useRemoveBill()
  const { mutateAsync: upsertBill } = useUpsertBill()

  const { visibleColumns, setVisibleColumns } = useColumnVisibility(
    INITIAL_VISIBLE_COLUMNS
  )
  const {
    detailedBill,
    closeUpsetBillModal,
    isUpsetBillModalOpen,
    openUpsetBillModal,
  } = useUpsertBillDisclousure()

  const headerColumns = useMemo(() => {
    if (visibleColumns === 'all') {
      return [...COLUMNS, OPTIONS_COLUMN]
    } else {
      return [
        ...COLUMNS.filter((column) => visibleColumns.has(column.key)),
        OPTIONS_COLUMN,
      ]
    }
  }, [visibleColumns])

  const renderCell = (
    bill: DetailedBill,
    key: keyof DetailedBill | 'options'
  ) => {
    switch (key) {
      case 'originalValue':
      case 'correctedValue':
        return (
          <div className="flex justify-center items-center">
            {CurrencyUtils.toBRL(bill[key])}
          </div>
        )
      case 'paymentDate':
      case 'dueDate':
        return (
          <div className="flex justify-center items-center">
            {DateUtils.toDDMMYYYY(bill[key])}
          </div>
        )
      case 'options':
        return (
          <div className="flex justify-center items-center gap-4">
            <Button color="primary" onClick={() => openUpsetBillModal(bill)}>
              Editar
            </Button>
            <Button color="danger" onClick={() => removeBill(bill.uuid)}>
              Excluir
            </Button>
          </div>
        )
      case 'daysOverdue':
        return (
          <div className="flex justify-center items-center">
            {bill[key] as number}
          </div>
        )
      default:
        return <div>{bill[key] as string}</div>
    }
  }

  const handleSubmit = async (bill: Bill) => {
    await upsertBill(bill)
  }

  return (
    <>
      <UpsertBillModal
        isOpen={isUpsetBillModalOpen}
        onClose={closeUpsetBillModal}
        bill={detailedBill}
        onSubmit={handleSubmit}
      />
      <Table
        aria-label="Tabela com a listagem de todas as contas"
        className="min-h-screen"
        topContent={
          <div className="flex flex-col">
            <div className="flex flex-row items-center justify-between w-full gap-4">
              <Input
                placeholder="Filtrar por nome"
                variant="bordered"
                value={filter}
                onValueChange={setFilter}
              />
              <ColumnsSelector
                columns={COLUMNS}
                setVisibleColumns={setVisibleColumns}
                visibleColumns={visibleColumns}
              />
              <AddBillButton onClick={() => openUpsetBillModal(undefined)} />
            </div>
            <div className="flex flex-row items-center justify-between w-full gap-4 mt-4">
              <h6 className="text-sm">
                Total de itens: {data?.page.totalElements || 0}
              </h6>
              <PageSizeDropdown
                handlePageSize={handlePageSize}
                options={PAGE_SIZE_OPTIONS}
                requestedPageSize={pageRequest.size}
              />
            </div>
          </div>
        }
        bottomContent={
          data &&
          data.page.totalPages > 1 && (
            <div className="flex w-full justify-center">
              <Pagination
                isCompact
                showControls
                showShadow
                color="primary"
                page={data.page.number}
                total={data.page.totalPages}
                onChange={handlePage}
              />
            </div>
          )
        }
      >
        <TableHeader columns={headerColumns}>
          {(column) => (
            <TableColumn key={column.key}>
              <div className="flex flex-row items-center justify-center gap-2">
                {column.label}
                {column.sortable && (
                  <ChevronIcon
                    onClick={() =>
                      handleSort(column.key as keyof AvailableSortColumns)
                    }
                  />
                )}
              </div>
            </TableColumn>
          )}
        </TableHeader>
        <TableBody
          items={data?.content || []}
          emptyContent={<div>Não há dados para serem exibidos</div>}
          isLoading={isFetching}
          loadingContent={<Spinner size="lg" aria-label="Loading..." />}
        >
          {(bill) => (
            <TableRow key={bill.uuid}>
              {(columnKey) => (
                <TableCell key={columnKey}>
                  {renderCell(bill, columnKey as keyof DetailedBill)}
                </TableCell>
              )}
            </TableRow>
          )}
        </TableBody>
      </Table>
    </>
  )
}
