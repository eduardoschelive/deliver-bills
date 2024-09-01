import {
  Button,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownTrigger,
  Selection,
} from '@nextui-org/react'

type ColumnsSelectorProps = {
  visibleColumns: Selection
  setVisibleColumns: (columns: Selection) => void
  columns: Array<{ key: string; label: string }>
}

export function ColumnsSelector({
  setVisibleColumns,
  visibleColumns,
  columns,
}: ColumnsSelectorProps) {
  return (
    <Dropdown>
      <DropdownTrigger>
        <Button>Colunas</Button>
      </DropdownTrigger>
      <DropdownMenu
        disallowEmptySelection
        aria-label="Selecione as colunas que deseja visualizar"
        closeOnSelect={false}
        selectionMode="multiple"
        selectedKeys={visibleColumns}
        onSelectionChange={setVisibleColumns}
      >
        {columns.map((column) => (
          <DropdownItem key={column.key} value={column.key}>
            {column.label}
          </DropdownItem>
        ))}
      </DropdownMenu>
    </Dropdown>
  )
}
