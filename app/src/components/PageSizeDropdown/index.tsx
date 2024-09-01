import {
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownTrigger,
} from '@nextui-org/react'

type PageSizeDropdownProps = {
  requestedPageSize: number
  options: number[]
  handlePageSize: (size: number) => void
}

export function PageSizeDropdown({
  handlePageSize,
  options,
  requestedPageSize,
}: PageSizeDropdownProps) {
  return (
    <Dropdown>
      <DropdownTrigger>
        <h6 className="text-sm">Items por página: {requestedPageSize}</h6>
      </DropdownTrigger>
      <DropdownMenu
        disallowEmptySelection
        aria-label="Selecione as colunas que deseja visualizar"
      >
        {options.map((size) => (
          <DropdownItem
            key={size}
            textValue={`${size} items`}
            onClick={() => handlePageSize(size)}
          >
            {size}
          </DropdownItem>
        ))}
      </DropdownMenu>
    </Dropdown>
  )
}
