import React from 'react'
import PropTypes from 'prop-types'
import { Confirm } from 'App/Components/Alert'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'

const DeleteBtn = ({
  connectionId,
  title,
  confirmTitle,
  confirmMessage,
  deleteHandler,
  localizations,
  fetches,
}) => {
  return (
    <ButtonWithLoader
      title={title}
      loading={fetches.fetchingDeleteConnection}
      onPressHandler={() =>
        Confirm({
          title: confirmTitle,
          message: confirmMessage,
          ok: () => deleteHandler(connectionId),
          localizations: localizations,
        })
      }
      small
      transparent
    />
  )
}

DeleteBtn.propTypes = {
  connectionId: PropTypes.number.isRequired,
  title: PropTypes.string.isRequired,
  confirmTitle: PropTypes.string.isRequired,
  confirmMessage: PropTypes.string.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default DeleteBtn
